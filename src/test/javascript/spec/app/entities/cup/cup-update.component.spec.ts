/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import CupUpdateComponent from '@/entities/cup/cup-update.vue';
import CupClass from '@/entities/cup/cup-update.component';
import CupService from '@/entities/cup/cup.service';

import PlayerService from '@/entities/player/player.service';

import NewsPaperService from '@/entities/news-paper/news-paper.service';

import CupBoardService from '@/entities/cup-board/cup-board.service';
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
  describe('Cup Management Update Component', () => {
    let wrapper: Wrapper<CupClass>;
    let comp: CupClass;
    let cupServiceStub: SinonStubbedInstance<CupService>;

    beforeEach(() => {
      cupServiceStub = sinon.createStubInstance<CupService>(CupService);

      wrapper = shallowMount<CupClass>(CupUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          cupService: () => cupServiceStub,
          alertService: () => new AlertService(),

          playerService: () =>
            sinon.createStubInstance<PlayerService>(PlayerService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          newsPaperService: () =>
            sinon.createStubInstance<NewsPaperService>(NewsPaperService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          cupBoardService: () =>
            sinon.createStubInstance<CupBoardService>(CupBoardService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('load', () => {
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        comp.cup = entity;
        cupServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cupServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.cup = entity;
        cupServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cupServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCup = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        cupServiceStub.find.resolves(foundCup);
        cupServiceStub.retrieve.resolves([foundCup]);

        // WHEN
        comp.beforeRouteEnter({ params: { cupId: '9fec3727-3421-4967-b213-ba36557ca194' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.cup).toBe(foundCup);
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
