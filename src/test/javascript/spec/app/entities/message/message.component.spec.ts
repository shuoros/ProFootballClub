/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import MessageComponent from '@/entities/message/message.vue';
import MessageClass from '@/entities/message/message.component';
import MessageService from '@/entities/message/message.service';
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
  describe('Message Management Component', () => {
    let wrapper: Wrapper<MessageClass>;
    let comp: MessageClass;
    let messageServiceStub: SinonStubbedInstance<MessageService>;

    beforeEach(() => {
      messageServiceStub = sinon.createStubInstance<MessageService>(MessageService);
      messageServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<MessageClass>(MessageComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          messageService: () => messageServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      messageServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '9fec3727-3421-4967-b213-ba36557ca194' }] });

      // WHEN
      comp.retrieveAllMessages();
      await comp.$nextTick();

      // THEN
      expect(messageServiceStub.retrieve.called).toBeTruthy();
      expect(comp.messages[0]).toEqual(expect.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      messageServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: '9fec3727-3421-4967-b213-ba36557ca194' });
      expect(messageServiceStub.retrieve.callCount).toEqual(1);

      comp.removeMessage();
      await comp.$nextTick();

      // THEN
      expect(messageServiceStub.delete.called).toBeTruthy();
      expect(messageServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
