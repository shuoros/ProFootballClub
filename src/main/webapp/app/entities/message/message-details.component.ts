import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMessage } from '@/shared/model/message.model';
import MessageService from './message.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class MessageDetails extends Vue {
  @Inject('messageService') private messageService: () => MessageService;
  @Inject('alertService') private alertService: () => AlertService;

  public message: IMessage = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.messageId) {
        vm.retrieveMessage(to.params.messageId);
      }
    });
  }

  public retrieveMessage(messageId) {
    this.messageService()
      .find(messageId)
      .then(res => {
        this.message = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
