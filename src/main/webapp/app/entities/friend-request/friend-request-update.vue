<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proFootballClubApp.friendRequest.home.createOrEditLabel"
          data-cy="FriendRequestCreateUpdateHeading"
          v-text="$t('proFootballClubApp.friendRequest.home.createOrEditLabel')"
        >
          Create or edit a FriendRequest
        </h2>
        <div>
          <div class="form-group" v-if="friendRequest.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="friendRequest.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.friendRequest.from')" for="friend-request-from">From</label>
            <select class="form-control" id="friend-request-from" data-cy="from" name="from" v-model="friendRequest.from">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="friendRequest.from && teamOption.id === friendRequest.from.id ? friendRequest.from : teamOption"
                v-for="teamOption in teams"
                :key="teamOption.id"
              >
                {{ teamOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.friendRequest.tu')" for="friend-request-tu">Tu</label>
            <select class="form-control" id="friend-request-tu" data-cy="tu" name="tu" v-model="friendRequest.tu">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="friendRequest.tu && teamOption.id === friendRequest.tu.id ? friendRequest.tu : teamOption"
                v-for="teamOption in teams"
                :key="teamOption.id"
              >
                {{ teamOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.friendRequest.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./friend-request-update.component.ts"></script>
