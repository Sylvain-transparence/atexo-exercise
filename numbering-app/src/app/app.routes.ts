import { Routes } from '@angular/router';
import {ConfigurationsComponent} from "./components/configurations/configurations.component";
import {GenerateComponent} from "./components/generate/generate.component";

export const routes: Routes = [
    { path: 'config', component: ConfigurationsComponent },
    { path: 'generate', component: GenerateComponent },
    { path: '', redirectTo: '/config', pathMatch: 'full' },
];
