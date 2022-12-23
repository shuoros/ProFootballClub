/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import LeagueBoardComponent from '@/entities/league-board/league-board.vue';
import LeagueBoardClass from '@/entities/league-board/league-board.component';
import LeagueBoardService from '@/entities/league-board/league-board.service';
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
  describe('LeagueBoard Management Component', () => {
    let wrapper: Wrapper<LeagueBoardClass>;
    let comp: LeagueBoardClass;
    let leagueBoardServiceStub: SinonStubbedInstance<LeagueBoardService>;

    beforeEach(() => {
      leagueBoardServiceStub = sinon.createStubInstance<LeagueBoardService>(LeagueBoardService);
      leagueBoardServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<LeagueBoardClass>(LeagueBoardComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          leagueBoardService: () => leagueBoardServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      leagueBoardServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '9fec3727-3421-4967-b213-ba36557ca194' }] });

      // WHEN
      comp.retrieveAllLeagueBoards();
      await comp.$nextTick();

      // THEN
      expect(leagueBoardServiceStub.retrieve.called).toBeTruthy();
      expect(comp.leagueBoards[0]).toEqual(expect.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      leagueBoardServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: '9fec3727-3421-4967-b213-ba36557ca194' });
      expect(leagueBoardServiceStub.retrieve.callCount).toEqual(1);

      comp.removeLeagueBoard();
      await comp.$nextTick();

      // THEN
      expect(leagueBoardServiceStub.delete.called).toBeTruthy();
      expect(leagueBoardServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
