import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {NumberingConfigurationDto} from '../../models/numbering-models';
import {ConfigurationService} from '../../services/configuration.service';

@Component({
  selector: 'app-current-config',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './current-config.component.html',
  styleUrls: ['./current-config.component.scss']
})
export class CurrentConfigComponent implements OnInit {
  currentConfig: NumberingConfigurationDto | null = null;
  showDetails: boolean = false;

  constructor(private configService: ConfigurationService) {
  }

  ngOnInit() {
    this.configService.getCurrentConfig().subscribe(config => {
      this.currentConfig = config;
    });
  }

  get criteriaCount(): number {
    return this.currentConfig?.criteriaConfigurations.length || 0;
  }

  toggleDetails() {
    this.showDetails = !this.showDetails;
  }
}
