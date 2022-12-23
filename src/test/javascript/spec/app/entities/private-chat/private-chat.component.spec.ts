/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PrivateChatComponent from '@/entities/private-chat/private-chat.vue';
import PrivateChatClass from '@/entities/private-chat/private-chat.component';
import PrivateChatService from '@/entities/private-chat/private-chat.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('PrivateChat Management Component', () => {
    let wrapper: Wrapper<PrivateChatClass>;
    let comp: PrivateChatClass;
    let privateChatServiceStub: SinonStubbedInstance<PrivateChatService>;

    beforeEach(() => {
      privateChatServiceStub = sinon.createStubInstance<PrivateChatService>(PrivateChatService);
      privateChatServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PrivateChatClass>(PrivateChatComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          privateChatService: () => privateChatServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      privateChatServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '9fec3727-3421-4967-b213-ba36557ca194' }] });

      // WHEN
      comp.retrieveAllPrivateChats();
      await comp.$nextTick();

      // THEN
      expect(privateChatServiceStub.retrieve.called).toBeTruthy();
      expect(comp.privateChats[0]).toEqual(expect.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      privateChatServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: '9fec3727-3421-4967-b213-ba36557ca194' });
      expect(privateChatServiceStub.retrieve.callCount).toEqual(1);

      comp.removePrivateChat();
      await comp.$nextTick();

      // THEN
      expect(privateChatServiceStub.delete.called).toBeTruthy();
      expect(privateChatServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
