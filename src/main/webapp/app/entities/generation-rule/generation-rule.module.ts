import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { GenerationRuleComponent } from './list/generation-rule.component';
import { GenerationRuleDetailComponent } from './detail/generation-rule-detail.component';
import { GenerationRuleUpdateComponent } from './update/generation-rule-update.component';
import { GenerationRuleDeleteDialogComponent } from './delete/generation-rule-delete-dialog.component';
import { GenerationRuleRoutingModule } from './route/generation-rule-routing.module';

@NgModule({
  imports: [SharedModule, GenerationRuleRoutingModule],
  declarations: [
    GenerationRuleComponent,
    GenerationRuleDetailComponent,
    GenerationRuleUpdateComponent,
    GenerationRuleDeleteDialogComponent,
  ],
  entryComponents: [GenerationRuleDeleteDialogComponent],
})
export class GenerationRuleModule {}
