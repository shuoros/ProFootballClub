/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import TransferComponent from '@/entities/transfer/transfer.vue';
import TransferClass from '@/entities/transfer/transfer.component';
import TransferService from '@/entities/transfer/transfer.service';
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
  describe('Transfer Management Component', () => {
    let wrapper: Wrapper<TransferClass>;
    let comp: TransferClass;
    let transferServiceStub: SinonStubbedInstance<TransferService>;

    beforeEach(() => {
      transferServiceStub = sinon.createStubInstance<TransferService>(TransferService);
      transferServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<TransferClass>(TransferComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          transferService: () => transferServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      transferServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '9fec3727-3421-4967-b213-ba36557ca194' }] });

      // WHEN
      comp.retrieveAllTransfers();
      await comp.$nextTick();

      // THEN
      expect(transferServiceStub.retrieve.called).toBeTruthy();
      expect(comp.transfers[0]).toEqual(expect.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      transferServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: '9fec3727-3421-4967-b213-ba36557ca194' });
      expect(transferServiceStub.retrieve.callCount).toEqual(1);

      comp.removeTransfer();
      await comp.$nextTick();

      // THEN
      expect(transferServiceStub.delete.called).toBeTruthy();
      expect(transferServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
