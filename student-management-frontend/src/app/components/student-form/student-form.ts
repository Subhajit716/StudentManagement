import { Component, OnInit, computed, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Observable } from 'rxjs';

import { Student, StudentRequest } from '../../models/student';
import { getErrorMessage } from '../../services/error-message';
import { NotificationService } from '../../services/notification.service';
import { StudentService } from '../../services/student.service';

type FieldName = keyof StudentRequest;

/** One component for BOTH "Add student" (/students/new) and "Edit student" (/students/:id/edit). */
@Component({
  selector: 'app-student-form',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './student-form.html',
})
export class StudentForm implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly studentService = inject(StudentService);
  private readonly notifications = inject(NotificationService);

  protected readonly genders = ['Male', 'Female', 'Other'];
  protected readonly today = new Date().toISOString().split('T')[0]; // latest allowed birth date

  protected readonly studentId = signal<number | null>(null);
  protected readonly isEditMode = computed(() => this.studentId() !== null);
  protected readonly loading = signal(false);
  protected readonly saving = signal(false);
  protected readonly loadError = signal<string | null>(null);

  // The same rules as the backend (StudentRequestDTO), so users see problems before sending
  protected readonly form = this.fb.nonNullable.group({
    firstName: ['', [Validators.required, Validators.maxLength(50)]],
    lastName: ['', [Validators.required, Validators.maxLength(50)]],
    email: ['', [Validators.required, Validators.email, Validators.maxLength(100)]],
    phoneNumber: ['', [Validators.required, Validators.pattern(/^\+?[0-9]{10,15}$/)]],
    department: ['', [Validators.required, Validators.maxLength(100)]],
    course: ['', [Validators.required, Validators.maxLength(100)]],
    gender: ['', [Validators.required]],
    dateOfBirth: ['', [Validators.required]],
  });

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam === null) {
      return; // no id in the URL: we are adding a new student
    }

    const id = Number(idParam);
    this.studentId.set(id);
    this.loading.set(true);

    this.studentService.getById(id).subscribe({
      next: (student: Student) => {
        this.form.patchValue(student); // fills the form fields with the saved values
        this.loading.set(false);
      },
      error: (err) => {
        this.loadError.set(getErrorMessage(err));
        this.loading.set(false);
      },
    });
  }

  // true when the field is wrong AND the user already touched it (or tried to submit)
  protected isInvalid(name: FieldName): boolean {
    const control = this.form.controls[name];
    return control.invalid && (control.touched || control.dirty);
  }

  protected hasError(name: FieldName, errorCode: string): boolean {
    return this.form.controls[name].hasError(errorCode);
  }

  protected onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched(); // reveals every validation message
      return;
    }

    const data: StudentRequest = this.form.getRawValue();
    const id = this.studentId();
    const request$: Observable<Student> =
      id === null ? this.studentService.create(data) : this.studentService.update(id, data);

    this.saving.set(true);
    request$.subscribe({
      next: (saved) => {
        const verb = id === null ? 'added' : 'updated';
        this.notifications.success(
          `${saved.firstName} ${saved.lastName} was ${verb} successfully.`,
        );
        this.router.navigate(['/students']);
      },
      error: (err) => {
        this.notifications.error(getErrorMessage(err));
        this.saving.set(false);
      },
    });
  }
}
