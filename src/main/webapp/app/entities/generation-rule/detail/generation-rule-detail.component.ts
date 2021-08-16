import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGenerationRule } from '../generation-rule.model';

@Component({
  selector: 'jhi-generation-rule-detail',
  templateUrl: './generation-rule-detail.component.html',
})
export class GenerationRuleDetailComponent implements OnInit {
  generationRule: IGenerationRule | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ generationRule }) => {
      this.generationRule = generationRule;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
