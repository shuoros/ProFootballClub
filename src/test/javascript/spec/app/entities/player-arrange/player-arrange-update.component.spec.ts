/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PlayerArrangeUpdateComponent from '@/entities/player-arrange/player-arrange-update.vue';
import PlayerArrangeClass from '@/entities/player-arrange/player-arrange-update.component';
import PlayerArrangeService from '@/entities/player-arrange/player-arrange.service';

import PlayerService from '@/entities/player/player.service';

import CompositionService from '@/entities/composition/composition.service';
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
  describe('PlayerArrange Management Update Component', () => {
    let wrapper: Wrapper<PlayerArrangeClass>;
    let comp: PlayerArrangeClass;
    let playerArrangeServiceStub: SinonStubbedInstance<PlayerArrangeService>;

    beforeEach(() => {
      playerArrangeServiceStub = sinon.createStubInstance<PlayerArrangeService>(PlayerArrangeService);

      wrapper = shallowMount<PlayerArrangeClass>(PlayerArrangeUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          playerArrangeService: () => playerArrangeServiceStub,
          alertService: () => new AlertService(),

          playerService: () =>
            sinon.createStubInstance<PlayerService>(PlayerService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          compositionService: () =>
            sinon.createStubInstance<CompositionService>(CompositionService, {
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
        comp.playerArrange = entity;
        playerArrangeServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(playerArrangeServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.playerArrange = entity;
        playerArrangeServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(playerArrangeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPlayerArrange = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        playerArrangeServiceStub.find.resolves(foundPlayerArrange);
        playerArrangeServiceStub.retrieve.resolves([foundPlayerArrange]);

        // WHEN
        comp.beforeRouteEnter({ params: { playerArrangeId: '9fec3727-3421-4967-b213-ba36557ca194' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.playerArrange).toBe(foundPlayerArrange);
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
