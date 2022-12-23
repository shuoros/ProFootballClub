<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proFootballClubApp.privateChat.home.createOrEditLabel"
          data-cy="PrivateChatCreateUpdateHeading"
          v-text="$t('proFootballClubApp.privateChat.home.createOrEditLabel')"
        >
          Create or edit a PrivateChat
        </h2>
        <div>
          <div class="form-group" v-if="privateChat.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="privateChat.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.privateChat.league')" for="private-chat-league">League</label>
            <select class="form-control" id="private-chat-league" data-cy="league" name="league" v-model="privateChat.league">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="privateChat.league && leagueOption.id === privateChat.league.id ? privateChat.league : leagueOption"
                v-for="leagueOption in leagues"
                :key="leagueOption.id"
              >
                {{ leagueOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.privateChat.cup')" for="private-chat-cup">Cup</label>
            <select class="form-control" id="private-chat-cup" data-cy="cup" name="cup" v-model="privateChat.cup">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="privateChat.cup && cupOption.id === privateChat.cup.id ? privateChat.cup : cupOption"
                v-for="cupOption in cups"
                :key="cupOption.id"
              >
                {{ cupOption.id }}
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
            :disabled="$v.privateChat.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./private-chat-update.component.ts"></script>
