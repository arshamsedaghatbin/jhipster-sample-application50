import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'time-slot',
        data: { pageTitle: 'jhipsterSampleApplication50App.timeSlot.home.title' },
        loadChildren: () => import('./time-slot/time-slot.module').then(m => m.TimeSlotModule),
      },
      {
        path: 'reservation',
        data: { pageTitle: 'jhipsterSampleApplication50App.reservation.home.title' },
        loadChildren: () => import('./reservation/reservation.module').then(m => m.ReservationModule),
      },
      {
        path: 'generation-rule',
        data: { pageTitle: 'jhipsterSampleApplication50App.generationRule.home.title' },
        loadChildren: () => import('./generation-rule/generation-rule.module').then(m => m.GenerationRuleModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
