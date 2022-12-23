import { Component, Vue, Inject } from 'vue-property-decorator';

import { INewsPaper } from '@/shared/model/news-paper.model';
import NewsPaperService from './news-paper.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class NewsPaperDetails extends Vue {
  @Inject('newsPaperService') private newsPaperService: () => NewsPaperService;
  @Inject('alertService') private alertService: () => AlertService;

  public newsPaper: INewsPaper = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.newsPaperId) {
        vm.retrieveNewsPaper(to.params.newsPaperId);
      }
    });
  }

  public retrieveNewsPaper(newsPaperId) {
    this.newsPaperService()
      .find(newsPaperId)
      .then(res => {
        this.newsPaper = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
