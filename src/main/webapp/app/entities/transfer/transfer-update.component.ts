import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import PlayerService from '@/entities/player/player.service';
import { IPlayer } from '@/shared/model/player.model';

import { ITransfer, Transfer } from '@/shared/model/transfer.model';
import TransferService from './transfer.service';

const validations: any = {
  transfer: {
    price: {
      required,
      numeric,
    },
    password: {},
    bought: {},
  },
};

@Component({
  validations,
})
export default class TransferUpdate extends Vue {
  @Inject('transferService') private transferService: () => TransferService;
  @Inject('alertService') private alertService: () => AlertService;

  public transfer: ITransfer = new Transfer();

  @Inject('playerService') private playerService: () => PlayerService;

  public players: IPlayer[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.transferId) {
        vm.retrieveTransfer(to.params.transferId);
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
    if (this.transfer.id) {
      this.transferService()
        .update(this.transfer)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.transfer.updated', { param: param.id });
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
      this.transferService()
        .create(this.transfer)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.transfer.created', { param: param.id });
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

  public retrieveTransfer(transferId): void {
    this.transferService()
      .find(transferId)
      .then(res => {
        this.transfer = res;
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
