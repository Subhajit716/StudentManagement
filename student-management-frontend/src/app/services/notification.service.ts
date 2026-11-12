import { Injectable, signal } from '@angular/core';

export interface Notification {
  type: 'success' | 'error';
  text: string;
}

/** Holds the success / error banner shown at the top of every page. */
@Injectable({ providedIn: 'root' })
export class NotificationService {
  readonly current = signal<Notification | null>(null);
  private timer?: ReturnType<typeof setTimeout>;

  success(text: string): void {
    this.show('success', text);
  }

  error(text: string): void {
    this.show('error', text);
  }

  clear(): void {
    clearTimeout(this.timer);
    this.current.set(null);
  }

  private show(type: Notification['type'], text: string): void {
    clearTimeout(this.timer);
    this.current.set({ type, text });
    // hide automatically after 5 seconds
    this.timer = setTimeout(() => this.current.set(null), 5000);
  }
}
