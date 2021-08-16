import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGenerationRule, GenerationRule } from '../generation-rule.model';
import { GenerationRuleService } from '../service/generation-rule.service';

@Injectable({ providedIn: 'root' })
export class GenerationRuleRoutingResolveService implements Resolve<IGenerationRule> {
  constructor(protected service: GenerationRuleService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGenerationRule> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((generationRule: HttpResponse<GenerationRule>) => {
          if (generationRule.body) {
            return of(generationRule.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GenerationRule());
  }
}
