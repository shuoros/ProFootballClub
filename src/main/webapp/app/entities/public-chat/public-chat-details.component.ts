import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPublicChat } from '@/shared/model/public-chat.model';
import PublicChatService from './public-chat.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class PublicChatDetails extends Vue {
  @Inject('publicChatService') private publicChatService: () => PublicChatService;
  @Inject('alertService') private alertService: () => AlertService;

  public publicChat: IPublicChat = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.publicChatId) {
        vm.retrievePublicChat(to.params.publicChatId);
      }
    });
  }

  public retrievePublicChat(publicChatId) {
    this.publicChatService()
      .find(publicChatId)
      .then(res => {
        this.publicChat = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
