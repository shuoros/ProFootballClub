import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import LeagueService from '@/entities/league/league.service';
import { ILeague } from '@/shared/model/league.model';

import CupService from '@/entities/cup/cup.service';
import { ICup } from '@/shared/model/cup.model';

import { INewsPaper, NewsPaper } from '@/shared/model/news-paper.model';
import NewsPaperService from './news-paper.service';

const validations: any = {
  newsPaper: {
    news: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class NewsPaperUpdate extends Vue {
  @Inject('newsPaperService') private newsPaperService: () => NewsPaperService;
  @Inject('alertService') private alertService: () => AlertService;

  public newsPaper: INewsPaper = new NewsPaper();

  @Inject('leagueService') private leagueService: () => LeagueService;

  public leagues: ILeague[] = [];

  @Inject('cupService') private cupService: () => CupService;

  public cups: ICup[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.newsPaperId) {
        vm.retrieveNewsPaper(to.params.newsPaperId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.newsPaper.id) {
      this.newsPaperService()
        .update(this.newsPaper)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.newsPaper.updated', { param: param.id });
          return (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.newsPaperService()
        .create(this.newsPaper)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.newsPaper.created', { param: param.id });
          (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveNewsPaper(newsPaperId): void {
    this.newsPaperService()
      .find(newsPaperId)
      .then(res => {
        this.newsPaper = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.leagueService()
      .retrieve()
      .then(res => {
        this.leagues = res.data;
      });
    this.cupService()
      .retrieve()
      .then(res => {
        this.cups = res.data;
      });
  }
}
