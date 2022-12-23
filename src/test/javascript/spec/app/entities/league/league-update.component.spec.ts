/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import LeagueUpdateComponent from '@/entities/league/league-update.vue';
import LeagueClass from '@/entities/league/league-update.component';
import LeagueService from '@/entities/league/league.service';

import PlayerService from '@/entities/player/player.service';

import NewsPaperService from '@/entities/news-paper/news-paper.service';

import MatchService from '@/entities/match/match.service';

import LeagueBoardService from '@/entities/league-board/league-board.service';
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
  describe('League Management Update Component', () => {
    let wrapper: Wrapper<LeagueClass>;
    let comp: LeagueClass;
    let leagueServiceStub: SinonStubbedInstance<LeagueService>;

    beforeEach(() => {
      leagueServiceStub = sinon.createStubInstance<LeagueService>(LeagueService);

      wrapper = shallowMount<LeagueClass>(LeagueUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          leagueService: () => leagueServiceStub,
          alertService: () => new AlertService(),

          playerService: () =>
            sinon.createStubInstance<PlayerService>(PlayerService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          newsPaperService: () =>
            sinon.createStubInstance<NewsPaperService>(NewsPaperService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          matchService: () =>
            sinon.createStubInstance<MatchService>(MatchService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          leagueBoardService: () =>
            sinon.createStubInstance<LeagueBoardService>(LeagueBoardService, {
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
        comp.league = entity;
        leagueServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(leagueServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.league = entity;
        leagueServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(leagueServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundLeague = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        leagueServiceStub.find.resolves(foundLeague);
        leagueServiceStub.retrieve.resolves([foundLeague]);

        // WHEN
        comp.beforeRouteEnter({ params: { leagueId: '9fec3727-3421-4967-b213-ba36557ca194' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.league).toBe(foundLeague);
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
