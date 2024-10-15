// models/numbering-models.ts

export interface NumberingConfigurationDto {
  counter: number;
  currentCounter: number;
  criteriaConfigurations: CriterionConfigurationDto[];
}

export interface CriterionConfigurationDto {
  order: number;
  type: CriterionTypeDto;
  prefix: string;
  suffix: string;
  length: number;
}

export enum CriterionTypeDto {
  LAST_NAME = 'LAST_NAME',
  FIRST_NAME = 'FIRST_NAME',
  BIRTH_YEAR = 'BIRTH_YEAR',
  COUNTER = 'COUNTER'
}

export interface RegisteredDto {
  firstName: string;
  lastName: string;
  birthDate: string;  // Format: 'YYYY-MM-DD'
  counter: number;
}
