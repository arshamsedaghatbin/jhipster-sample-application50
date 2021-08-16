import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IGenerationRule } from '../generation-rule.model';
import { GenerationRuleService } from '../service/generation-rule.service';

@Component({
  templateUrl: './generation-rule-delete-dialog.component.html',
})
export class GenerationRuleDeleteDialogComponent {
  generationRule?: IGenerationRule;

  constructor(protected generationRuleService: GenerationRuleService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.generationRuleService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
