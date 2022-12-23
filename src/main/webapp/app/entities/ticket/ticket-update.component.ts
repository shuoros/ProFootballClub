import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import TeamService from '@/entities/team/team.service';
import { ITeam } from '@/shared/model/team.model';

import MessageService from '@/entities/message/message.service';
import { IMessage } from '@/shared/model/message.model';

import { ITicket, Ticket } from '@/shared/model/ticket.model';
import TicketService from './ticket.service';

const validations: any = {
  ticket: {
    subject: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class TicketUpdate extends Vue {
  @Inject('ticketService') private ticketService: () => TicketService;
  @Inject('alertService') private alertService: () => AlertService;

  public ticket: ITicket = new Ticket();

  @Inject('teamService') private teamService: () => TeamService;

  public teams: ITeam[] = [];

  @Inject('messageService') private messageService: () => MessageService;

  public messages: IMessage[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.ticketId) {
        vm.retrieveTicket(to.params.ticketId);
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
    if (this.ticket.id) {
      this.ticketService()
        .update(this.ticket)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.ticket.updated', { param: param.id });
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
      this.ticketService()
        .create(this.ticket)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proFootballClubApp.ticket.created', { param: param.id });
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

  public retrieveTicket(ticketId): void {
    this.ticketService()
      .find(ticketId)
      .then(res => {
        this.ticket = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.teamService()
      .retrieve()
      .then(res => {
        this.teams = res.data;
      });
    this.messageService()
      .retrieve()
      .then(res => {
        this.messages = res.data;
      });
  }
}
