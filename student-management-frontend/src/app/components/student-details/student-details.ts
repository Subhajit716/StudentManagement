import { DatePipe } from '@angular/common';
import { Component, OnInit, inject, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';

import { Student } from '../../models/student';
import { getErrorMessage } from '../../services/error-message';
import { StudentService } from '../../services/student.service';

@Component({
  selector: 'app-student-details',
  imports: [RouterLink, DatePipe],
  templateUrl: './student-details.html',
})
export class StudentDetails implements OnInit {
  private readonly route = inject(ActivatedRoute);
  private readonly studentService = inject(StudentService);

  protected readonly student = signal<Student | null>(null);
  protected readonly loading = signal(true);
  protected readonly errorMessage = signal<string | null>(null);

  ngOnInit(): void {
    // Reads the ":id" part of the URL /students/:id
    const id = Number(this.route.snapshot.paramMap.get('id'));

    this.studentService.getById(id).subscribe({
      next: (data) => {
        this.student.set(data);
        this.loading.set(false);
      },
      error: (err) => {
        this.errorMessage.set(getErrorMessage(err));
        this.loading.set(false);
      },
    });
  }
}
