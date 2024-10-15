import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import {NumberingService} from '../../services/numbering.service';

@Component({
  selector: 'app-generate',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './generate.component.html',
  styleUrls: ['./generate.component.scss']
})
export class GenerateComponent {
  generateForm: FormGroup;
  generatedNumber: string | null = null;
  isLoading: boolean = false;
  error: string | null = null;

  constructor(
    private fb: FormBuilder,
    private numberingService: NumberingService
  ) {
    this.generateForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      birthDate: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.generateForm.valid) {
      this.isLoading = true;
      this.error = null;
      this.numberingService.generateNumber(this.generateForm.value).subscribe(
        number => {
          this.generatedNumber = number;
          this.isLoading = false;
        },
        error => {
          console.error('Error generating number', error);
          this.error = 'An error occurred while generating the number. Please try again.';
          this.isLoading = false;
        }
      );
    }
  }
}
