import { Routes } from '@angular/router';

import { StudentDetails } from './components/student-details/student-details';
import { StudentForm } from './components/student-form/student-form';
import { StudentList } from './components/student-list/student-list';

export const routes: Routes = [
  { path: '', redirectTo: 'students', pathMatch: 'full' },
  { path: 'students', component: StudentList },
  // "new" must come BEFORE ":id", otherwise "new" would be read as an id
  { path: 'students/new', component: StudentForm },
  { path: 'students/:id/edit', component: StudentForm },
  { path: 'students/:id', component: StudentDetails },
  { path: '**', redirectTo: 'students' },
];
