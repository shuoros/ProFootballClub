/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import TransferDetailComponent from '@/entities/transfer/transfer-details.vue';
import TransferClass from '@/entities/transfer/transfer-details.component';
import TransferService from '@/entities/transfer/transfer.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Transfer Management Detail Component', () => {
    let wrapper: Wrapper<TransferClass>;
    let comp: TransferClass;
    let transferServiceStub: SinonStubbedInstance<TransferService>;

    beforeEach(() => {
      transferServiceStub = sinon.createStubInstance<TransferService>(TransferService);

      wrapper = shallowMount<TransferClass>(TransferDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { transferService: () => transferServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTransfer = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        transferServiceStub.find.resolves(foundTransfer);

        // WHEN
        comp.retrieveTransfer('9fec3727-3421-4967-b213-ba36557ca194');
        await comp.$nextTick();

        // THEN
        expect(comp.transfer).toBe(foundTransfer);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTransfer = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        transferServiceStub.find.resolves(foundTransfer);

        // WHEN
        comp.beforeRouteEnter({ params: { transferId: '9fec3727-3421-4967-b213-ba36557ca194' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.transfer).toBe(foundTransfer);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
