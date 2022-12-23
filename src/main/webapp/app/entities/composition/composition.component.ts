import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IComposition } from '@/shared/model/composition.model';

import CompositionService from './composition.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Composition extends Vue {
  @Inject('compositionService') private compositionService: () => CompositionService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: string = null;

  public compositions: IComposition[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCompositions();
  }

  public clear(): void {
    this.retrieveAllCompositions();
  }

  public retrieveAllCompositions(): void {
    this.isFetching = true;
    this.compositionService()
      .retrieve()
      .then(
        res => {
          this.compositions = res.data;
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

  public prepareRemove(instance: IComposition): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeComposition(): void {
    this.compositionService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('proFootballClubApp.composition.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllCompositions();
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
