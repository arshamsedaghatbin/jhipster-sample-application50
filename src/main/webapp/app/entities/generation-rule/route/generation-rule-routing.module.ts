import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { GenerationRuleComponent } from '../list/generation-rule.component';
import { GenerationRuleDetailComponent } from '../detail/generation-rule-detail.component';
import { GenerationRuleUpdateComponent } from '../update/generation-rule-update.component';
import { GenerationRuleRoutingResolveService } from './generation-rule-routing-resolve.service';

const generationRuleRoute: Routes = [
  {
    path: '',
    component: GenerationRuleComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GenerationRuleDetailComponent,
    resolve: {
      generationRule: GenerationRuleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GenerationRuleUpdateComponent,
    resolve: {
      generationRule: GenerationRuleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GenerationRuleUpdateComponent,
    resolve: {
      generationRule: GenerationRuleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(generationRuleRoute)],
  exports: [RouterModule],
})
export class GenerationRuleRoutingModule {}
