import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('http://localhost:4200/adminside/login?returnUrl=%2Fauthor');
  await page.getByRole('textbox', { name: 'E-mail-cím' }).click();
  await page.getByRole('textbox', { name: 'E-mail-cím' }).fill('editor@example.com');
  await page.getByRole('textbox', { name: 'Jelszó' }).click();
  await page.getByRole('textbox', { name: 'Jelszó' }).fill('NewsDemo2026!');
  await page.getByRole('button', { name: 'Bejelentkezés' }).click();
});