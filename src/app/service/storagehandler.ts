import { Injectable, inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root',
})
export class LocalStorageService {
  private readonly platformId = inject(PLATFORM_ID);

  // Set a value in local storage
  setItem(key: string, value: string): void {
    if (isPlatformBrowser(this.platformId)) localStorage.setItem(key, value);
  }

  // Get a value from local storage
  getItem(key: string): string | null {
    return isPlatformBrowser(this.platformId) ? localStorage.getItem(key) : null;
  }

  // Remove a value from local storage
  removeItem(key: string): void {
    if (isPlatformBrowser(this.platformId)) localStorage.removeItem(key);
  }

  // Clear all items from local storage
  clear(): void {
    if (isPlatformBrowser(this.platformId)) localStorage.clear();
  }
}
