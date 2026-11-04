import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { map } from 'rxjs';
import { AuthenticationService } from './authentication.service';

export const authenticationGuard: CanActivateFn = (_route, state) => {
  const auth = inject(AuthenticationService);
  const router = inject(Router);

  return auth.refreshAuthState().pipe(
    map(isAuthenticated => isAuthenticated || router.createUrlTree(
      ['/adminside/login'],
      { queryParams: { returnUrl: state.url } },
    )),
  );
};
