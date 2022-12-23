/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import LeagueComponent from '@/entities/league/league.vue';
import LeagueClass from '@/entities/league/league.component';
import LeagueService from '@/entities/league/league.service';
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
  describe('League Management Component', () => {
    let wrapper: Wrapper<LeagueClass>;
    let comp: LeagueClass;
    let leagueServiceStub: SinonStubbedInstance<LeagueService>;

    beforeEach(() => {
      leagueServiceStub = sinon.createStubInstance<LeagueService>(LeagueService);
      leagueServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<LeagueClass>(LeagueComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          leagueService: () => leagueServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      leagueServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '9fec3727-3421-4967-b213-ba36557ca194' }] });

      // WHEN
      comp.retrieveAllLeagues();
      await comp.$nextTick();

      // THEN
      expect(leagueServiceStub.retrieve.called).toBeTruthy();
      expect(comp.leagues[0]).toEqual(expect.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      leagueServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: '9fec3727-3421-4967-b213-ba36557ca194' });
      expect(leagueServiceStub.retrieve.callCount).toEqual(1);

      comp.removeLeague();
      await comp.$nextTick();

      // THEN
      expect(leagueServiceStub.delete.called).toBeTruthy();
      expect(leagueServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
