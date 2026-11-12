// The shape of a student exactly as the Spring Boot API sends it (JSON field names must match).
export interface Student {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  department: string;
  course: string;
  gender: string;
  dateOfBirth: string; // "yyyy-MM-dd", for example "2003-04-12"
}

// What we send when creating or updating: the same fields, but without the id.
export type StudentRequest = Omit<Student, 'id'>;
