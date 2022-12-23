import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import PlayerService from '@/entities/player/player.service';
import { IPlayer } from '@/shared/model/player.model';

import { ITrain, Train } from '@/shared/model/train.model';
import TrainService from './train.service';
import { Training } from '@/shared/model/enumerations/training.model';

const validations: any = {
  train: {
    training: {},
    finishes: {},
  },
};

@Component({
  validations,
})
export default class TrainUpdate extends Vue {
  @Inject('trainService') private trainService: () => TrainService;
  @Inject('alertService') private alertService: () => AlertService;

  public train: ITrain = new Train();

  @Inject('playerService') private playerService: () => PlayerService;

  public players: IPlayer[] = [];
  public trainingValues: string[] = Object.keys(Training);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.trainId) {
        vm.retrieveTrain(to.params.trainId);
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
    if (this.train.id) {
      this.trainService()
        .update(this.train)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.train.updated', { param: param.id });
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
      this.trainService()
        .create(this.train)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.train.created', { param: param.id });
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

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.train[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.train[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.train[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.train[field] = null;
    }
  }

  public retrieveTrain(trainId): void {
    this.trainService()
      .find(trainId)
      .then(res => {
        res.finishes = new Date(res.finishes);
        this.train = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.playerService()
      .retrieve()
      .then(res => {
        this.players = res.data;
      });
  }
}
