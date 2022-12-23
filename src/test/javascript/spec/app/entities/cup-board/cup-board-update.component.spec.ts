/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import CupBoardUpdateComponent from '@/entities/cup-board/cup-board-update.vue';
import CupBoardClass from '@/entities/cup-board/cup-board-update.component';
import CupBoardService from '@/entities/cup-board/cup-board.service';

import MatchService from '@/entities/match/match.service';

import CupService from '@/entities/cup/cup.service';
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
  describe('CupBoard Management Update Component', () => {
    let wrapper: Wrapper<CupBoardClass>;
    let comp: CupBoardClass;
    let cupBoardServiceStub: SinonStubbedInstance<CupBoardService>;

    beforeEach(() => {
      cupBoardServiceStub = sinon.createStubInstance<CupBoardService>(CupBoardService);

      wrapper = shallowMount<CupBoardClass>(CupBoardUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          cupBoardService: () => cupBoardServiceStub,
          alertService: () => new AlertService(),

          matchService: () =>
            sinon.createStubInstance<MatchService>(MatchService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          cupService: () =>
            sinon.createStubInstance<CupService>(CupService, {
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
        comp.cupBoard = entity;
        cupBoardServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cupBoardServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.cupBoard = entity;
        cupBoardServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cupBoardServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCupBoard = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        cupBoardServiceStub.find.resolves(foundCupBoard);
        cupBoardServiceStub.retrieve.resolves([foundCupBoard]);

        // WHEN
        comp.beforeRouteEnter({ params: { cupBoardId: '9fec3727-3421-4967-b213-ba36557ca194' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.cupBoard).toBe(foundCupBoard);
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
