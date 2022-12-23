/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import TransferUpdateComponent from '@/entities/transfer/transfer-update.vue';
import TransferClass from '@/entities/transfer/transfer-update.component';
import TransferService from '@/entities/transfer/transfer.service';

import PlayerService from '@/entities/player/player.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Transfer Management Update Component', () => {
    let wrapper: Wrapper<TransferClass>;
    let comp: TransferClass;
    let transferServiceStub: SinonStubbedInstance<TransferService>;

    beforeEach(() => {
      transferServiceStub = sinon.createStubInstance<TransferService>(TransferService);

      wrapper = shallowMount<TransferClass>(TransferUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          transferService: () => transferServiceStub,
          alertService: () => new AlertService(),

          playerService: () =>
            sinon.createStubInstance<PlayerService>(PlayerService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        comp.transfer = entity;
        transferServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(transferServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.transfer = entity;
        transferServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(transferServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTransfer = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        transferServiceStub.find.resolves(foundTransfer);
        transferServiceStub.retrieve.resolves([foundTransfer]);

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
