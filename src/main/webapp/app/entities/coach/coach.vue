<template>
  <div>
    <h2 id="page-heading" data-cy="CoachHeading">
      <span v-text="$t('proFootballClubApp.coach.home.title')" id="coach-heading">Coaches</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('proFootballClubApp.coach.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'CoachCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-coach"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('proFootballClubApp.coach.home.createLabel')"> Create a new Coach </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && coaches && coaches.length === 0">
      <span v-text="$t('proFootballClubApp.coach.home.notFound')">No coaches found</span>
    </div>
    <div class="table-responsive" v-if="coaches && coaches.length > 0">
      <table class="table table-striped" aria-describedby="coaches">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.coach.name')">Name</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.coach.banned')">Banned</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.coach.abandoned')">Abandoned</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.coach.subscribed')">Subscribed</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.coach.plan')">Plan</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.coach.user')">User</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="coach in coaches" :key="coach.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CoachView', params: { coachId: coach.id } }">{{ coach.id }}</router-link>
            </td>
            <td>{{ coach.name }}</td>
            <td>{{ coach.banned }}</td>
            <td>{{ coach.abandoned }}</td>
            <td>{{ coach.subscribed }}</td>
            <td v-text="$t('proFootballClubApp.Plan.' + coach.plan)">{{ coach.plan }}</td>
            <td>
              {{ coach.user ? coach.user.id : '' }}
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CoachView', params: { coachId: coach.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CoachEdit', params: { coachId: coach.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(coach)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="proFootballClubApp.coach.delete.question" data-cy="coachDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-coach-heading" v-text="$t('proFootballClubApp.coach.delete.question', { id: removeId })">
          Are you sure you want to delete this Coach?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-coach"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeCoach()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./coach.component.ts"></script>
