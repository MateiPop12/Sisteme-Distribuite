import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import { ChartModule } from 'primeng/chart';
import {Reading} from "../../models/reading";

@Component({
  selector: 'chart',
  standalone: true,
  imports: [
    ChartModule,
  ],
  templateUrl: './chart.component.html',
  styleUrl: './chart.component.css'
})
export class ChartComponent implements OnChanges {
  @Input() readings!: Reading[];

  chartData:any;
  chartOptions: any;

  constructor() {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['readings'] && this.readings) {
      this.transformData();
    }
  }

  private transformData(): void {
    // Extract labels (X-axis) and dataset (Y-axis)
    const labels = this.readings.map((reading) => new Date(reading.timestamp).toLocaleTimeString());
    const data = this.readings.map((reading) => reading.measurement_value);

    this.chartData = {
      labels: labels,
      datasets: [
        {
          label: 'Measurement Values',
          data: data,
          borderColor: '#42A5F5',
          fill: false,
          tension: 0.4,
        },
      ],
    };

    // Configure white labels for axes and legend
    this.chartOptions = {
      plugins: {
        legend: {
          labels: {
            color: '#FFFFFF', // White color for legend text
          },
        },
      },
      scales: {
        x: {
          ticks: {
            color: '#FFFFFF', // White color for X-axis labels
          },
          grid: {
            color: 'rgba(255, 255, 255, 0.2)', // Optional: Light white grid lines
          },
        },
        y: {
          ticks: {
            color: '#FFFFFF', // White color for Y-axis labels
          },
          grid: {
            color: 'rgba(255, 255, 255, 0.2)', // Optional: Light white grid lines
          },
        },
      },
    };
  }
}
