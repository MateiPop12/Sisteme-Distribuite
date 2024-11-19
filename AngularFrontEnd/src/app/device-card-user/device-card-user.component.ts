import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {Device} from "../../models/device";
import {DeviceService} from "../../services/device.service";
import {ChartComponent} from "../chart/chart.component";
import {Reading} from "../../models/reading";
import {MonitorService} from "../../services/monitor.service";

@Component({
  selector: 'device-card-user',
  standalone: true,
    imports: [
        FormsModule,
        NgForOf,
        ChartComponent,
        NgIf
    ],
  templateUrl: './device-card-user.component.html',
  styleUrl: './device-card-user.component.css'
})
export class DeviceCardUserComponent {

  @Input() device! : Device;
  @Output() updateList= new EventEmitter<void>();
  showChartToggle : boolean = false;
  chartData : Reading[] = [];

  constructor(private deviceService: DeviceService,
              private monitorService: MonitorService) {}


  deleteDevice() {
    this.deviceService.deleteDevice(this.device.id).subscribe(
      response => {
        this.updateList.emit();
        console.log('Device deleted successfully:', response);
      },
      error => {
        console.log('Error deleting device:', error);
      }
    );
  }

  updateDevice() {
    this.deviceService.updateDevice(this.device).subscribe(
      response => {
        this.updateList.emit();
        console.log('Device updated successfully', response);
      },
      error => {
        console.log('Error updating device:', error);
      }
    );
  }

  showChart() {
    this.showChartToggle=!this.showChartToggle;
    this.monitorService.getReadingsByDeviceId(this.device.id).subscribe(
      data=>this.chartData = data,
      error=> console.log("Error loading chart data:",error)
    )
    console.log(this.chartData);
  }
}
