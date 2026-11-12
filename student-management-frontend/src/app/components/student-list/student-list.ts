import { Component, OnInit, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';

import { Student } from '../../models/student';
import { getErrorMessage } from '../../services/error-message';
import { NotificationService } from '../../services/notification.service';
import { StudentService } from '../../services/student.service';

@Component({
  selector: 'app-student-list',
  imports: [RouterLink],
  templateUrl: './student-list.html',
})
export class StudentList implements OnInit {
  private readonly studentService = inject(StudentService);
  private readonly notifications = inject(NotificationService);

  protected readonly students = signal<Student[]>([]);
  protected readonly loading = signal(true);
  protected readonly errorMessage = signal<string | null>(null);

  ngOnInit(): void {
    this.loadStudents();
  }

  protected loadStudents(): void {
    this.loading.set(true);
    this.errorMessage.set(null);

    this.studentService.getAll().subscribe({
      next: (data) => {
        this.students.set(data);
        this.loading.set(false);
      },
      error: (err) => {
        this.errorMessage.set(getErrorMessage(err));
        this.loading.set(false);
      },
    });
  }

  protected deleteStudent(student: Student): void {
    const confirmed = window.confirm(
      `Delete ${student.firstName} ${student.lastName}? This cannot be undone.`,
    );
    if (!confirmed) {
      return;
    }

    this.studentService.delete(student.id).subscribe({
      next: () => {
        this.notifications.success(`${student.firstName} ${student.lastName} was deleted.`);
        this.loadStudents();
      },
      error: (err) => this.notifications.error(getErrorMessage(err)),
    });
  }
}
