import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPrivateChat } from '@/shared/model/private-chat.model';
import PrivateChatService from './private-chat.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class PrivateChatDetails extends Vue {
  @Inject('privateChatService') private privateChatService: () => PrivateChatService;
  @Inject('alertService') private alertService: () => AlertService;

  public privateChat: IPrivateChat = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.privateChatId) {
        vm.retrievePrivateChat(to.params.privateChatId);
      }
    });
  }

  public retrievePrivateChat(privateChatId) {
    this.privateChatService()
      .find(privateChatId)
      .then(res => {
        this.privateChat = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
