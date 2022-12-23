/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import NewsPaperComponent from '@/entities/news-paper/news-paper.vue';
import NewsPaperClass from '@/entities/news-paper/news-paper.component';
import NewsPaperService from '@/entities/news-paper/news-paper.service';
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
  describe('NewsPaper Management Component', () => {
    let wrapper: Wrapper<NewsPaperClass>;
    let comp: NewsPaperClass;
    let newsPaperServiceStub: SinonStubbedInstance<NewsPaperService>;

    beforeEach(() => {
      newsPaperServiceStub = sinon.createStubInstance<NewsPaperService>(NewsPaperService);
      newsPaperServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<NewsPaperClass>(NewsPaperComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          newsPaperService: () => newsPaperServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      newsPaperServiceStub.retrieve.resolves({ headers: {}, data: [{ id: '9fec3727-3421-4967-b213-ba36557ca194' }] });

      // WHEN
      comp.retrieveAllNewsPapers();
      await comp.$nextTick();

      // THEN
      expect(newsPaperServiceStub.retrieve.called).toBeTruthy();
      expect(comp.newsPapers[0]).toEqual(expect.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      newsPaperServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: '9fec3727-3421-4967-b213-ba36557ca194' });
      expect(newsPaperServiceStub.retrieve.callCount).toEqual(1);

      comp.removeNewsPaper();
      await comp.$nextTick();

      // THEN
      expect(newsPaperServiceStub.delete.called).toBeTruthy();
      expect(newsPaperServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
