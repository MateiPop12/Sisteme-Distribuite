use std::error::Error;
use std::fs::File;
use std::thread;
use std::time::Duration as StdDuration;
use csv::ReaderBuilder;
use chrono::{Local, DateTime, Duration as ChronoDuration};
use lapin::{Connection, ConnectionProperties, options::*, types::FieldTable, BasicProperties, ExchangeKind};
use serde::{Serialize, Serializer};
use tokio::runtime;

#[derive(Debug, Serialize)]
struct SensorData {
    device_id: i32,

    #[serde(serialize_with = "serialize_datetime_local")]
    timestamp: DateTime<Local>,

    value: f64,
}

fn serialize_datetime_local<S>(dt: &DateTime<Local>, serializer: S) -> Result<S::Ok, S::Error>
where
    S: Serializer,
{
    let timestamp_str = dt.to_rfc3339(); // Format timestamp as RFC3339 string
    serializer.serialize_str(&timestamp_str)
}

async fn send_message(
    channel: lapin::Channel,
    exchange_name: &str,
    routing_key: &str,
    message: &str,
) -> Result<(), lapin::Error> {
    channel
        .basic_publish(
            exchange_name,
            routing_key,
            BasicPublishOptions::default(),
            message.as_bytes(),
            BasicProperties::default(),
        )
        .await?;
    Ok(())
}

async fn connect_to_rabbitmq(
    addr: &str,
    exchange_name: &str,
    queue_name: &str,
    routing_key: &str,
) -> Result<lapin::Channel, lapin::Error> {
    let conn = Connection::connect(
        addr,
        ConnectionProperties::default(),
    )
        .await?;
    let channel = conn.create_channel().await?;

    // Use ExchangeKind::Direct here
    channel
        .exchange_declare(
            exchange_name,
            ExchangeKind::Topic,
            ExchangeDeclareOptions {
                durable: true,
                ..Default::default()
            },
            FieldTable::default(),
        )
        .await?;

    channel
        .queue_declare(
            queue_name,
            QueueDeclareOptions {
                durable: true,
                ..Default::default()
            },
            FieldTable::default(),
        )
        .await?;
    channel
        .queue_bind(
            queue_name,
            exchange_name,
            routing_key,
            QueueBindOptions::default(),
            FieldTable::default(),
        )
        .await?;
    Ok(channel)
}

fn main() -> Result<(), Box<dyn Error>> {
    // Replace these values with your RabbitMQ connection details
    let addr = "amqp://guest:guest@localhost:5672/%2f";
    let exchange_name = "SD_exchange";
    let queue_name = "sim_queue";
    let routing_key = "sim_routingKey";

    // Open the file
    let file_path = "src/sensor.csv";
    let file = File::open(&file_path)?;
    let mut rdr = ReaderBuilder::new().has_headers(false).from_reader(file);
    let mut hourly_increment = 0;
    let device_id = 8;

    // Connect to RabbitMQ
    let runtime = runtime::Runtime::new()?;
    let channel = runtime.block_on(connect_to_rabbitmq(addr, exchange_name, queue_name, routing_key))?;

    // Iterate over each record in the CSV file
    for result in rdr.records() {
        // Extract the record
        let record = result?;

        // Iterate over each field in the record
        for field in record.iter() {
            // Find the position of the dot
            if let Some(dot_pos) = field.find('.') {
                // Extract the part after the dot
                let fractional_part = &field[dot_pos + 1..];

                // Prepend "0." and convert to a floating-point number
                if let Ok(value) = format!("0.{}", fractional_part).parse::<f64>() {
                    // Get the current timestamp
                    let timestamp: DateTime<Local> = Local::now();

                    // Add 3 hours to the timestamp
                    let timestamp_plus_3_hours = timestamp + ChronoDuration::hours(hourly_increment);

                    // Create a SensorData struct
                    let sensor_data = SensorData {
                        timestamp: timestamp_plus_3_hours,
                        device_id,
                        value,
                    };

                    // Convert the struct to a JSON string (for simplicity)
                    let message = serde_json::to_string(&sensor_data)?;

                    // Send the message to RabbitMQ
                    runtime.block_on(send_message(channel.clone(), exchange_name, routing_key, &message))?;

                    // Print the timestamp, device ID, and reading
                    println!("Timestamp: {}", timestamp_plus_3_hours);
                    println!("DeviceId: {}", device_id);
                    println!("Reading: {}", value);
                    println!();
                    hourly_increment += 1;
                    // Introduce a 2-second delay
                    thread::sleep(StdDuration::from_secs(1));
                }
            }
        }
    }
    Ok(())
}
