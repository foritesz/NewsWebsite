import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  await page.goto('http://localhost:4200/');
  await page.getByRole('link', { name: 'Health' }).click();
  await page.getByRole('link', { name: 'Új fejlesztések a budapesti közlekedésben Health Új fejlesztések a budapesti kö' }).click();
  await page.getByRole('link', { name: 'Vissza a főoldalra' }).click();
  await page.getByRole('link', { name: 'Sport', exact: true }).click();
  await page.getByRole('link', { name: 'Sikeres hétvégét zártak a magyar sportolók Sport Sikeres hétvégét zártak a' }).click();
  await page.getByRole('link', { name: 'Vissza a főoldalra' }).click();
});