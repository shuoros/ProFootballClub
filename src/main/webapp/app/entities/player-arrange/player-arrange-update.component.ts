import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import PlayerService from '@/entities/player/player.service';
import { IPlayer } from '@/shared/model/player.model';

import CompositionService from '@/entities/composition/composition.service';
import { IComposition } from '@/shared/model/composition.model';

import { IPlayerArrange, PlayerArrange } from '@/shared/model/player-arrange.model';
import PlayerArrangeService from './player-arrange.service';
import { Post } from '@/shared/model/enumerations/post.model';

const validations: any = {
  playerArrange: {
    post: {},
  },
};

@Component({
  validations,
})
export default class PlayerArrangeUpdate extends Vue {
  @Inject('playerArrangeService') private playerArrangeService: () => PlayerArrangeService;
  @Inject('alertService') private alertService: () => AlertService;

  public playerArrange: IPlayerArrange = new PlayerArrange();

  @Inject('playerService') private playerService: () => PlayerService;

  public players: IPlayer[] = [];

  @Inject('compositionService') private compositionService: () => CompositionService;

  public compositions: IComposition[] = [];
  public postValues: string[] = Object.keys(Post);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.playerArrangeId) {
        vm.retrievePlayerArrange(to.params.playerArrangeId);
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
    if (this.playerArrange.id) {
      this.playerArrangeService()
        .update(this.playerArrange)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.playerArrange.updated', { param: param.id });
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
      this.playerArrangeService()
        .create(this.playerArrange)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.playerArrange.created', { param: param.id });
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

  public retrievePlayerArrange(playerArrangeId): void {
    this.playerArrangeService()
      .find(playerArrangeId)
      .then(res => {
        this.playerArrange = res;
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
    this.compositionService()
      .retrieve()
      .then(res => {
        this.compositions = res.data;
      });
  }
}
