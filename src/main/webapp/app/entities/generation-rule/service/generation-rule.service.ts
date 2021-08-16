import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGenerationRule, getGenerationRuleIdentifier } from '../generation-rule.model';

export type EntityResponseType = HttpResponse<IGenerationRule>;
export type EntityArrayResponseType = HttpResponse<IGenerationRule[]>;

@Injectable({ providedIn: 'root' })
export class GenerationRuleService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/generation-rules');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(generationRule: IGenerationRule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(generationRule);
    return this.http
      .post<IGenerationRule>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(generationRule: IGenerationRule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(generationRule);
    return this.http
      .put<IGenerationRule>(`${this.resourceUrl}/${getGenerationRuleIdentifier(generationRule) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(generationRule: IGenerationRule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(generationRule);
    return this.http
      .patch<IGenerationRule>(`${this.resourceUrl}/${getGenerationRuleIdentifier(generationRule) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IGenerationRule>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IGenerationRule[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addGenerationRuleToCollectionIfMissing(
    generationRuleCollection: IGenerationRule[],
    ...generationRulesToCheck: (IGenerationRule | null | undefined)[]
  ): IGenerationRule[] {
    const generationRules: IGenerationRule[] = generationRulesToCheck.filter(isPresent);
    if (generationRules.length > 0) {
      const generationRuleCollectionIdentifiers = generationRuleCollection.map(
        generationRuleItem => getGenerationRuleIdentifier(generationRuleItem)!
      );
      const generationRulesToAdd = generationRules.filter(generationRuleItem => {
        const generationRuleIdentifier = getGenerationRuleIdentifier(generationRuleItem);
        if (generationRuleIdentifier == null || generationRuleCollectionIdentifiers.includes(generationRuleIdentifier)) {
          return false;
        }
        generationRuleCollectionIdentifiers.push(generationRuleIdentifier);
        return true;
      });
      return [...generationRulesToAdd, ...generationRuleCollection];
    }
    return generationRuleCollection;
  }

  protected convertDateFromClient(generationRule: IGenerationRule): IGenerationRule {
    return Object.assign({}, generationRule, {
      validFrom: generationRule.validFrom?.isValid() ? generationRule.validFrom.toJSON() : undefined,
      startSlotsTime: generationRule.startSlotsTime?.isValid() ? generationRule.startSlotsTime.toJSON() : undefined,
      endSlotsTime: generationRule.endSlotsTime?.isValid() ? generationRule.endSlotsTime.toJSON() : undefined,
      createdAt: generationRule.createdAt?.isValid() ? generationRule.createdAt.toJSON() : undefined,
      updatedAt: generationRule.updatedAt?.isValid() ? generationRule.updatedAt.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.validFrom = res.body.validFrom ? dayjs(res.body.validFrom) : undefined;
      res.body.startSlotsTime = res.body.startSlotsTime ? dayjs(res.body.startSlotsTime) : undefined;
      res.body.endSlotsTime = res.body.endSlotsTime ? dayjs(res.body.endSlotsTime) : undefined;
      res.body.createdAt = res.body.createdAt ? dayjs(res.body.createdAt) : undefined;
      res.body.updatedAt = res.body.updatedAt ? dayjs(res.body.updatedAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((generationRule: IGenerationRule) => {
        generationRule.validFrom = generationRule.validFrom ? dayjs(generationRule.validFrom) : undefined;
        generationRule.startSlotsTime = generationRule.startSlotsTime ? dayjs(generationRule.startSlotsTime) : undefined;
        generationRule.endSlotsTime = generationRule.endSlotsTime ? dayjs(generationRule.endSlotsTime) : undefined;
        generationRule.createdAt = generationRule.createdAt ? dayjs(generationRule.createdAt) : undefined;
        generationRule.updatedAt = generationRule.updatedAt ? dayjs(generationRule.updatedAt) : undefined;
      });
    }
    return res;
  }
}
