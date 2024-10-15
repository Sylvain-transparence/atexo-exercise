import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {ConfigurationService} from "../../services/configuration.service";

@Component({
    selector: 'app-config',
    standalone: true,
    imports: [CommonModule, ReactiveFormsModule],
    templateUrl: './configurations.component.html',
    styleUrls: ['./configurations.component.scss']
})
export class ConfigurationsComponent implements OnInit {
    configForm: FormGroup;
    MAX_CRITERIA = 4;
    errorMessage: string | null = null;

    constructor(
        private fb: FormBuilder,
        private configService: ConfigurationService
    ) {
        this.configForm = this.fb.group({
            counter: [0, Validators.required],
            criteriaConfigurations: this.fb.array([])
        });
    }

    ngOnInit() {
        this.loadConfig();
    }

    get criteriaConfigurations() {
        return this.configForm.get('criteriaConfigurations') as FormArray;
    }

    addCriterion() {
        if (this.criteriaConfigurations.length < this.MAX_CRITERIA) {
            const criterionGroup = this.fb.group({
                order: [this.criteriaConfigurations.length + 1, Validators.required],
                type: ['', Validators.required],
                prefix: [''],
                suffix: [''],
                length: [0, Validators.required]
            });
            this.criteriaConfigurations.push(criterionGroup);
        }
    }

    removeCriterion(index: number) {
        this.criteriaConfigurations.removeAt(index);
    }

    loadConfig() {
        this.configService.getConfiguration().subscribe(
            config => {
                if (config) {
                    this.configForm.patchValue(config);
                    config.criteriaConfigurations.forEach(() => this.addCriterion());
                    this.configForm.patchValue(config);
                }
                this.errorMessage = null;
            },
            error => {
                console.error('Error loading configuration', error);
                this.errorMessage = 'Failed to load configuration. Please try again.';
            }
        );
    }

    onSubmit() {
        if (this.configForm.valid) {
            this.configService.saveConfiguration(this.configForm.value).subscribe(
                () => {
                    alert('Configuration saved successfully');
                    this.errorMessage = null;
                },
                error => {
                    console.error('Error saving configuration', error);
                    this.errorMessage = 'Failed to save configuration. Please try again.';
                }
            );
        }
    }

  onCounterChange(event: Event) {
    const inputElement = event.target as HTMLInputElement;
    let value = parseInt(inputElement.value, 10);

    if (isNaN(value) || value < 0) {
      value = 0;
    }

    this.configForm.patchValue({ counter: value });
  }

  get canAddCriterion() {
        return this.criteriaConfigurations.length < this.MAX_CRITERIA;
    }
}
