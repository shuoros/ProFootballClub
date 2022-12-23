/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import CompositionUpdateComponent from '@/entities/composition/composition-update.vue';
import CompositionClass from '@/entities/composition/composition-update.component';
import CompositionService from '@/entities/composition/composition.service';

import PlayerService from '@/entities/player/player.service';

import PlayerArrangeService from '@/entities/player-arrange/player-arrange.service';

import TeamService from '@/entities/team/team.service';
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
  describe('Composition Management Update Component', () => {
    let wrapper: Wrapper<CompositionClass>;
    let comp: CompositionClass;
    let compositionServiceStub: SinonStubbedInstance<CompositionService>;

    beforeEach(() => {
      compositionServiceStub = sinon.createStubInstance<CompositionService>(CompositionService);

      wrapper = shallowMount<CompositionClass>(CompositionUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          compositionService: () => compositionServiceStub,
          alertService: () => new AlertService(),

          playerService: () =>
            sinon.createStubInstance<PlayerService>(PlayerService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          playerArrangeService: () =>
            sinon.createStubInstance<PlayerArrangeService>(PlayerArrangeService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          teamService: () =>
            sinon.createStubInstance<TeamService>(TeamService, {
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
        comp.composition = entity;
        compositionServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(compositionServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.composition = entity;
        compositionServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(compositionServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundComposition = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        compositionServiceStub.find.resolves(foundComposition);
        compositionServiceStub.retrieve.resolves([foundComposition]);

        // WHEN
        comp.beforeRouteEnter({ params: { compositionId: '9fec3727-3421-4967-b213-ba36557ca194' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.composition).toBe(foundComposition);
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
