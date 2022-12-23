/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PublicChatComponent from '@/entities/public-chat/public-chat.vue';
import PublicChatClass from '@/entities/public-chat/public-chat.component';
import PublicChatService from '@/entities/public-chat/public-chat.service';
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
  describe('PublicChat Management Component', () => {
    let wrapper: Wrapper<PublicChatClass>;
    let comp: PublicChatClass;
    let publicChatServiceStub: SinonStubbedInstance<PublicChatService>;

    beforeEach(() => {
      publicChatServiceStub = sinon.createStubInstance<PublicChatService>(PublicChatService);
      publicChatServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PublicChatClass>(PublicChatComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          publicChatService: () => publicChatServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      publicChatServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '9fec3727-3421-4967-b213-ba36557ca194' }] });

      // WHEN
      comp.retrieveAllPublicChats();
      await comp.$nextTick();

      // THEN
      expect(publicChatServiceStub.retrieve.called).toBeTruthy();
      expect(comp.publicChats[0]).toEqual(expect.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      publicChatServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: '9fec3727-3421-4967-b213-ba36557ca194' });
      expect(publicChatServiceStub.retrieve.callCount).toEqual(1);

      comp.removePublicChat();
      await comp.$nextTick();

      // THEN
      expect(publicChatServiceStub.delete.called).toBeTruthy();
      expect(publicChatServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
