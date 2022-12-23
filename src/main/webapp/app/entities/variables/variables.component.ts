import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IVariables } from '@/shared/model/variables.model';

import VariablesService from './variables.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Variables extends Vue {
  @Inject('variablesService') private variablesService: () => VariablesService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: string = null;

  public variables: IVariables[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllVariabless();
  }

  public clear(): void {
    this.retrieveAllVariabless();
  }

  public retrieveAllVariabless(): void {
    this.isFetching = true;
    this.variablesService()
      .retrieve()
      .then(
        res => {
          this.variables = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IVariables): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeVariables(): void {
    this.variablesService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('proFootballClubApp.variables.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllVariabless();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
