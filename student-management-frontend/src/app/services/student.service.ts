import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

import { Student, StudentRequest } from '../models/student';

/**
 * All communication with the Spring Boot API lives here.
 * HttpClient does not send the request until someone calls .subscribe() on the returned Observable.
 */
@Injectable({ providedIn: 'root' })
export class StudentService {
  private readonly http = inject(HttpClient);

  // Backend address as seen from the BROWSER (the backend publishes port 8080 on your computer).
  private readonly apiUrl = 'http://localhost:8080/api/students';

  getAll(): Observable<Student[]> {
    return this.http.get<Student[]>(this.apiUrl);
  }

  getById(id: number): Observable<Student> {
    return this.http.get<Student>(`${this.apiUrl}/${id}`);
  }

  create(student: StudentRequest): Observable<Student> {
    return this.http.post<Student>(this.apiUrl, student);
  }

  update(id: number, student: StudentRequest): Observable<Student> {
    return this.http.put<Student>(`${this.apiUrl}/${id}`, student);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
