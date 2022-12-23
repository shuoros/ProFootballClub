/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import MatchComponent from '@/entities/match/match.vue';
import MatchClass from '@/entities/match/match.component';
import MatchService from '@/entities/match/match.service';
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
  describe('Match Management Component', () => {
    let wrapper: Wrapper<MatchClass>;
    let comp: MatchClass;
    let matchServiceStub: SinonStubbedInstance<MatchService>;

    beforeEach(() => {
      matchServiceStub = sinon.createStubInstance<MatchService>(MatchService);
      matchServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<MatchClass>(MatchComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          matchService: () => matchServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      matchServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '9fec3727-3421-4967-b213-ba36557ca194' }] });

      // WHEN
      comp.retrieveAllMatchs();
      await comp.$nextTick();

      // THEN
      expect(matchServiceStub.retrieve.called).toBeTruthy();
      expect(comp.matches[0]).toEqual(expect.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      matchServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: '9fec3727-3421-4967-b213-ba36557ca194' });
      expect(matchServiceStub.retrieve.callCount).toEqual(1);

      comp.removeMatch();
      await comp.$nextTick();

      // THEN
      expect(matchServiceStub.delete.called).toBeTruthy();
      expect(matchServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
