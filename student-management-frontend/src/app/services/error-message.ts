import { HttpErrorResponse } from '@angular/common/http';

/** Turns any HTTP error into one readable sentence for the user. */
export function getErrorMessage(error: unknown): string {
  if (error instanceof HttpErrorResponse) {
    // status 0 means the browser could not reach the server at all
    if (error.status === 0) {
      return 'Cannot reach the server. Check that the backend is running on port 8080.';
    }

    const body = error.error;
    if (body && typeof body === 'object') {
      // Validation errors from Spring: { fieldErrors: { email: "Email must be valid", ... } }
      const fieldErrors = body.fieldErrors as Record<string, string> | undefined;
      if (fieldErrors && Object.keys(fieldErrors).length > 0) {
        return Object.values(fieldErrors).join(' ');
      }
      if (typeof body.message === 'string') {
        return body.message;
      }
    }
    return `Request failed (status ${error.status}).`;
  }
  return 'Something went wrong. Please try again.';
}
