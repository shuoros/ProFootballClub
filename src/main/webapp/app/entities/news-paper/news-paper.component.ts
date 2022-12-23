import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { INewsPaper } from '@/shared/model/news-paper.model';

import NewsPaperService from './news-paper.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class NewsPaper extends Vue {
  @Inject('newsPaperService') private newsPaperService: () => NewsPaperService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: string = null;

  public newsPapers: INewsPaper[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllNewsPapers();
  }

  public clear(): void {
    this.retrieveAllNewsPapers();
  }

  public retrieveAllNewsPapers(): void {
    this.isFetching = true;
    this.newsPaperService()
      .retrieve()
      .then(
        res => {
          this.newsPapers = res.data;
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

  public prepareRemove(instance: INewsPaper): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeNewsPaper(): void {
    this.newsPaperService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('proFootballClubApp.newsPaper.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllNewsPapers();
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
